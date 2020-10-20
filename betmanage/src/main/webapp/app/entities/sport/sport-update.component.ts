import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISport, Sport } from 'app/shared/model/sport.model';
import { SportService } from './sport.service';

@Component({
  selector: 'jhi-sport-update',
  templateUrl: './sport-update.component.html',
})
export class SportUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    sportName: [null, [Validators.required]],
  });

  constructor(protected sportService: SportService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sport }) => {
      this.updateForm(sport);
    });
  }

  updateForm(sport: ISport): void {
    this.editForm.patchValue({
      id: sport.id,
      sportName: sport.sportName,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const sport = this.createFromForm();
    if (sport.id !== undefined) {
      this.subscribeToSaveResponse(this.sportService.update(sport));
    } else {
      this.subscribeToSaveResponse(this.sportService.create(sport));
    }
  }

  private createFromForm(): ISport {
    return {
      ...new Sport(),
      id: this.editForm.get(['id'])!.value,
      sportName: this.editForm.get(['sportName'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISport>>): void {
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
}
