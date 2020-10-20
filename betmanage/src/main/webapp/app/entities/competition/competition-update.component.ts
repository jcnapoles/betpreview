import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { ICompetition, Competition } from 'app/shared/model/competition.model';
import { CompetitionService } from './competition.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { ICountry } from 'app/shared/model/country.model';
import { CountryService } from 'app/entities/country/country.service';
import { ISport } from 'app/shared/model/sport.model';
import { SportService } from 'app/entities/sport/sport.service';

type SelectableEntity = ICountry | ISport;

@Component({
  selector: 'jhi-competition-update',
  templateUrl: './competition-update.component.html',
})
export class CompetitionUpdateComponent implements OnInit {
  isSaving = false;
  countries: ICountry[] = [];
  sports: ISport[] = [];

  editForm = this.fb.group({
    id: [],
    competitionName: [null, [Validators.required]],
    competitionLogo: [],
    competitionLogoContentType: [],
    isCup: [],
    active: [],
    type: [],
    sportscribeId: [],
    country: [],
    sport: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected competitionService: CompetitionService,
    protected countryService: CountryService,
    protected sportService: SportService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ competition }) => {
      this.updateForm(competition);

      this.countryService
        .query({ filter: 'competition-is-null' })
        .pipe(
          map((res: HttpResponse<ICountry[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: ICountry[]) => {
          if (!competition.country || !competition.country.id) {
            this.countries = resBody;
          } else {
            this.countryService
              .find(competition.country.id)
              .pipe(
                map((subRes: HttpResponse<ICountry>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ICountry[]) => (this.countries = concatRes));
          }
        });

      this.sportService.query().subscribe((res: HttpResponse<ISport[]>) => (this.sports = res.body || []));
    });
  }

  updateForm(competition: ICompetition): void {
    this.editForm.patchValue({
      id: competition.id,
      competitionName: competition.competitionName,
      competitionLogo: competition.competitionLogo,
      competitionLogoContentType: competition.competitionLogoContentType,
      isCup: competition.isCup,
      active: competition.active,
      type: competition.type,
      sportscribeId: competition.sportscribeId,
      country: competition.country,
      sport: competition.sport,
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: any, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('betmanageApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const competition = this.createFromForm();
    if (competition.id !== undefined) {
      this.subscribeToSaveResponse(this.competitionService.update(competition));
    } else {
      this.subscribeToSaveResponse(this.competitionService.create(competition));
    }
  }

  private createFromForm(): ICompetition {
    return {
      ...new Competition(),
      id: this.editForm.get(['id'])!.value,
      competitionName: this.editForm.get(['competitionName'])!.value,
      competitionLogoContentType: this.editForm.get(['competitionLogoContentType'])!.value,
      competitionLogo: this.editForm.get(['competitionLogo'])!.value,
      isCup: this.editForm.get(['isCup'])!.value,
      active: this.editForm.get(['active'])!.value,
      type: this.editForm.get(['type'])!.value,
      sportscribeId: this.editForm.get(['sportscribeId'])!.value,
      country: this.editForm.get(['country'])!.value,
      sport: this.editForm.get(['sport'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICompetition>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
