import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { ICompetition, Competition } from 'app/shared/model/competition.model';
import { CompetitionService } from './competition.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { ITeam } from 'app/shared/model/team.model';
import { TeamService } from 'app/entities/team/team.service';
import { ISport } from 'app/shared/model/sport.model';
import { SportService } from 'app/entities/sport/sport.service';
import { ICountry } from 'app/shared/model/country.model';
import { CountryService } from 'app/entities/country/country.service';

type SelectableEntity = ITeam | ISport | ICountry;

@Component({
  selector: 'jhi-competition-update',
  templateUrl: './competition-update.component.html',
})
export class CompetitionUpdateComponent implements OnInit {
  isSaving = false;
  teams: ITeam[] = [];
  sports: ISport[] = [];
  countries: ICountry[] = [];

  editForm = this.fb.group({
    id: [],
    competitionName: [null, [Validators.required]],
    competitionLogo: [],
    competitionLogoContentType: [],
    isCup: [],
    active: [],
    type: [],
    sportscribeId: [],
    teams: [],
    sport: [],
    country: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected competitionService: CompetitionService,
    protected teamService: TeamService,
    protected sportService: SportService,
    protected countryService: CountryService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ competition }) => {
      this.updateForm(competition);

      this.teamService.query().subscribe((res: HttpResponse<ITeam[]>) => (this.teams = res.body || []));

      this.sportService.query().subscribe((res: HttpResponse<ISport[]>) => (this.sports = res.body || []));

      this.countryService.query().subscribe((res: HttpResponse<ICountry[]>) => (this.countries = res.body || []));
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
      teams: competition.teams,
      sport: competition.sport,
      country: competition.country,
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

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
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
      teams: this.editForm.get(['teams'])!.value,
      sport: this.editForm.get(['sport'])!.value,
      country: this.editForm.get(['country'])!.value,
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

  getSelected(selectedVals: ITeam[], option: ITeam): ITeam {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
