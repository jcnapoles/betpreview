import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IMatchPreview, MatchPreview } from 'app/shared/model/match-preview.model';
import { MatchPreviewService } from './match-preview.service';
import { ICountry } from 'app/shared/model/country.model';
import { CountryService } from 'app/entities/country/country.service';

@Component({
  selector: 'jhi-match-preview-update',
  templateUrl: './match-preview-update.component.html',
})
export class MatchPreviewUpdateComponent implements OnInit {
  isSaving = false;
  countries: ICountry[] = [];

  editForm = this.fb.group({
    id: [],
    fixtureId: [null, [Validators.required]],
    blurbFull: [],
    hometeamId: [],
    visitorteamId: [],
    hometeamName: [],
    visitorteamName: [],
    leagueId: [],
    league: [],
    formationImg: [],
    fixtureImg: [],
    country: [],
  });

  constructor(
    protected matchPreviewService: MatchPreviewService,
    protected countryService: CountryService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ matchPreview }) => {
      this.updateForm(matchPreview);

      this.countryService
        .query({ filter: 'matchpreview-is-null' })
        .pipe(
          map((res: HttpResponse<ICountry[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: ICountry[]) => {
          if (!matchPreview.country || !matchPreview.country.id) {
            this.countries = resBody;
          } else {
            this.countryService
              .find(matchPreview.country.id)
              .pipe(
                map((subRes: HttpResponse<ICountry>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ICountry[]) => (this.countries = concatRes));
          }
        });
    });
  }

  updateForm(matchPreview: IMatchPreview): void {
    this.editForm.patchValue({
      id: matchPreview.id,
      fixtureId: matchPreview.fixtureId,
      blurbFull: matchPreview.blurbFull,
      hometeamId: matchPreview.hometeamId,
      visitorteamId: matchPreview.visitorteamId,
      hometeamName: matchPreview.hometeamName,
      visitorteamName: matchPreview.visitorteamName,
      leagueId: matchPreview.leagueId,
      league: matchPreview.league,
      formationImg: matchPreview.formationImg,
      fixtureImg: matchPreview.fixtureImg,
      country: matchPreview.country,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const matchPreview = this.createFromForm();
    if (matchPreview.id !== undefined) {
      this.subscribeToSaveResponse(this.matchPreviewService.update(matchPreview));
    } else {
      this.subscribeToSaveResponse(this.matchPreviewService.create(matchPreview));
    }
  }

  private createFromForm(): IMatchPreview {
    return {
      ...new MatchPreview(),
      id: this.editForm.get(['id'])!.value,
      fixtureId: this.editForm.get(['fixtureId'])!.value,
      blurbFull: this.editForm.get(['blurbFull'])!.value,
      hometeamId: this.editForm.get(['hometeamId'])!.value,
      visitorteamId: this.editForm.get(['visitorteamId'])!.value,
      hometeamName: this.editForm.get(['hometeamName'])!.value,
      visitorteamName: this.editForm.get(['visitorteamName'])!.value,
      leagueId: this.editForm.get(['leagueId'])!.value,
      league: this.editForm.get(['league'])!.value,
      formationImg: this.editForm.get(['formationImg'])!.value,
      fixtureImg: this.editForm.get(['fixtureImg'])!.value,
      country: this.editForm.get(['country'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMatchPreview>>): void {
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

  trackById(index: number, item: ICountry): any {
    return item.id;
  }
}
