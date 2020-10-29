import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { ITeam, Team } from 'app/shared/model/team.model';
import { TeamService } from './team.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IMatchPreview } from 'app/shared/model/match-preview.model';
import { MatchPreviewService } from 'app/entities/match-preview/match-preview.service';
import { ICompetition } from 'app/shared/model/competition.model';
import { CompetitionService } from 'app/entities/competition/competition.service';
import { ICountry } from 'app/shared/model/country.model';
import { CountryService } from 'app/entities/country/country.service';

type SelectableEntity = IMatchPreview | ICompetition | ICountry;

@Component({
  selector: 'jhi-team-update',
  templateUrl: './team-update.component.html',
})
export class TeamUpdateComponent implements OnInit {
  isSaving = false;
  matchpreviews: IMatchPreview[] = [];
  competitions: ICompetition[] = [];
  countries: ICountry[] = [];

  editForm = this.fb.group({
    id: [],
    teamName: [null, [Validators.required]],
    shortCode: [],
    isNationalTeam: [],
    teamLogo: [],
    teamLogoContentType: [],
    teamId: [],
    matchPreviews: [],
    competition: [],
    country: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected teamService: TeamService,
    protected matchPreviewService: MatchPreviewService,
    protected competitionService: CompetitionService,
    protected countryService: CountryService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ team }) => {
      this.updateForm(team);

      this.matchPreviewService.query().subscribe((res: HttpResponse<IMatchPreview[]>) => (this.matchpreviews = res.body || []));

      this.competitionService.query().subscribe((res: HttpResponse<ICompetition[]>) => (this.competitions = res.body || []));

      this.countryService.query().subscribe((res: HttpResponse<ICountry[]>) => (this.countries = res.body || []));
    });
  }

  updateForm(team: ITeam): void {
    this.editForm.patchValue({
      id: team.id,
      teamName: team.teamName,
      shortCode: team.shortCode,
      isNationalTeam: team.isNationalTeam,
      teamLogo: team.teamLogo,
      teamLogoContentType: team.teamLogoContentType,
      teamId: team.teamId,
      matchPreviews: team.matchPreviews,
      competition: team.competition,
      country: team.country,
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
    const team = this.createFromForm();
    if (team.id !== undefined) {
      this.subscribeToSaveResponse(this.teamService.update(team));
    } else {
      this.subscribeToSaveResponse(this.teamService.create(team));
    }
  }

  private createFromForm(): ITeam {
    return {
      ...new Team(),
      id: this.editForm.get(['id'])!.value,
      teamName: this.editForm.get(['teamName'])!.value,
      shortCode: this.editForm.get(['shortCode'])!.value,
      isNationalTeam: this.editForm.get(['isNationalTeam'])!.value,
      teamLogoContentType: this.editForm.get(['teamLogoContentType'])!.value,
      teamLogo: this.editForm.get(['teamLogo'])!.value,
      teamId: this.editForm.get(['teamId'])!.value,
      matchPreviews: this.editForm.get(['matchPreviews'])!.value,
      competition: this.editForm.get(['competition'])!.value,
      country: this.editForm.get(['country'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITeam>>): void {
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

  getSelected(selectedVals: IMatchPreview[], option: IMatchPreview): IMatchPreview {
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
