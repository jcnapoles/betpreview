import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IMatchPreview, MatchPreview } from 'app/shared/model/match-preview.model';
import { MatchPreviewService } from './match-preview.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { ITeam } from 'app/shared/model/team.model';
import { TeamService } from 'app/entities/team/team.service';
import { ITeamSocial } from 'app/shared/model/team-social.model';
import { TeamSocialService } from 'app/entities/team-social/team-social.service';
import { ICompetition } from 'app/shared/model/competition.model';
import { CompetitionService } from 'app/entities/competition/competition.service';
import { ICountry } from 'app/shared/model/country.model';
import { CountryService } from 'app/entities/country/country.service';

type SelectableEntity = ITeam | ITeamSocial | ICompetition | ICountry;

@Component({
  selector: 'jhi-match-preview-update',
  templateUrl: './match-preview-update.component.html',
})
export class MatchPreviewUpdateComponent implements OnInit {
  isSaving = false;
  hometeams: ITeam[] = [];
  visitorteams: ITeam[] = [];
  socials: ITeamSocial[] = [];
  competitions: ICompetition[] = [];
  countries: ICountry[] = [];
  dateDp: any;

  editForm = this.fb.group({
    id: [],
    blurbFull: [],
    fixtureId: [null, [Validators.required]],
    hometeamId: [],
    visitorteamId: [],
    hometeamName: [],
    visitorteamName: [],
    leagueId: [],
    league: [],
    fixtureImg: [],
    fixtureImgContentType: [],
    formationImg: [],
    formationImgContentType: [],
    startUtcTimestamp: [],
    venueName: [],
    venueCity: [],
    matchImg: [],
    matchImgContentType: [],
    matchImaTxt: [],
    headline: [],
    date: [],
    language: [],
    homeTeam: [],
    visitorTeam: [],
    social: [],
    competition: [],
    country: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected matchPreviewService: MatchPreviewService,
    protected teamService: TeamService,
    protected teamSocialService: TeamSocialService,
    protected competitionService: CompetitionService,
    protected countryService: CountryService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ matchPreview }) => {
      if (!matchPreview.id) {
        const today = moment().startOf('day');
        matchPreview.startUtcTimestamp = today;
      }

      this.updateForm(matchPreview);

      this.teamService
        .query({ filter: 'matchpreview-is-null' })
        .pipe(
          map((res: HttpResponse<ITeam[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: ITeam[]) => {
          if (!matchPreview.homeTeam || !matchPreview.homeTeam.id) {
            this.hometeams = resBody;
          } else {
            this.teamService
              .find(matchPreview.homeTeam.id)
              .pipe(
                map((subRes: HttpResponse<ITeam>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ITeam[]) => (this.hometeams = concatRes));
          }
        });

      this.teamService
        .query({ filter: 'matchpreview-is-null' })
        .pipe(
          map((res: HttpResponse<ITeam[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: ITeam[]) => {
          if (!matchPreview.visitorTeam || !matchPreview.visitorTeam.id) {
            this.visitorteams = resBody;
          } else {
            this.teamService
              .find(matchPreview.visitorTeam.id)
              .pipe(
                map((subRes: HttpResponse<ITeam>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ITeam[]) => (this.visitorteams = concatRes));
          }
        });

      this.teamSocialService
        .query({ filter: 'matchpreview-is-null' })
        .pipe(
          map((res: HttpResponse<ITeamSocial[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: ITeamSocial[]) => {
          if (!matchPreview.social || !matchPreview.social.id) {
            this.socials = resBody;
          } else {
            this.teamSocialService
              .find(matchPreview.social.id)
              .pipe(
                map((subRes: HttpResponse<ITeamSocial>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ITeamSocial[]) => (this.socials = concatRes));
          }
        });

      this.competitionService.query().subscribe((res: HttpResponse<ICompetition[]>) => (this.competitions = res.body || []));

      this.countryService.query().subscribe((res: HttpResponse<ICountry[]>) => (this.countries = res.body || []));
    });
  }

  updateForm(matchPreview: IMatchPreview): void {
    this.editForm.patchValue({
      id: matchPreview.id,
      blurbFull: matchPreview.blurbFull,
      fixtureId: matchPreview.fixtureId,
      hometeamId: matchPreview.hometeamId,
      visitorteamId: matchPreview.visitorteamId,
      hometeamName: matchPreview.hometeamName,
      visitorteamName: matchPreview.visitorteamName,
      leagueId: matchPreview.leagueId,
      league: matchPreview.league,
      fixtureImg: matchPreview.fixtureImg,
      fixtureImgContentType: matchPreview.fixtureImgContentType,
      formationImg: matchPreview.formationImg,
      formationImgContentType: matchPreview.formationImgContentType,
      startUtcTimestamp: matchPreview.startUtcTimestamp ? matchPreview.startUtcTimestamp.format(DATE_TIME_FORMAT) : null,
      venueName: matchPreview.venueName,
      venueCity: matchPreview.venueCity,
      matchImg: matchPreview.matchImg,
      matchImgContentType: matchPreview.matchImgContentType,
      matchImaTxt: matchPreview.matchImaTxt,
      headline: matchPreview.headline,
      date: matchPreview.date,
      language: matchPreview.language,
      homeTeam: matchPreview.homeTeam,
      visitorTeam: matchPreview.visitorTeam,
      social: matchPreview.social,
      competition: matchPreview.competition,
      country: matchPreview.country,
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
      blurbFull: this.editForm.get(['blurbFull'])!.value,
      fixtureId: this.editForm.get(['fixtureId'])!.value,
      hometeamId: this.editForm.get(['hometeamId'])!.value,
      visitorteamId: this.editForm.get(['visitorteamId'])!.value,
      hometeamName: this.editForm.get(['hometeamName'])!.value,
      visitorteamName: this.editForm.get(['visitorteamName'])!.value,
      leagueId: this.editForm.get(['leagueId'])!.value,
      league: this.editForm.get(['league'])!.value,
      fixtureImgContentType: this.editForm.get(['fixtureImgContentType'])!.value,
      fixtureImg: this.editForm.get(['fixtureImg'])!.value,
      formationImgContentType: this.editForm.get(['formationImgContentType'])!.value,
      formationImg: this.editForm.get(['formationImg'])!.value,
      startUtcTimestamp: this.editForm.get(['startUtcTimestamp'])!.value
        ? moment(this.editForm.get(['startUtcTimestamp'])!.value, DATE_TIME_FORMAT)
        : undefined,
      venueName: this.editForm.get(['venueName'])!.value,
      venueCity: this.editForm.get(['venueCity'])!.value,
      matchImgContentType: this.editForm.get(['matchImgContentType'])!.value,
      matchImg: this.editForm.get(['matchImg'])!.value,
      matchImaTxt: this.editForm.get(['matchImaTxt'])!.value,
      headline: this.editForm.get(['headline'])!.value,
      date: this.editForm.get(['date'])!.value,
      language: this.editForm.get(['language'])!.value,
      homeTeam: this.editForm.get(['homeTeam'])!.value,
      visitorTeam: this.editForm.get(['visitorTeam'])!.value,
      social: this.editForm.get(['social'])!.value,
      competition: this.editForm.get(['competition'])!.value,
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
