import { Component } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder } from '@angular/forms';
import { MatchPreviewService } from './match-preview.service';
import { ITeam } from 'app/shared/model/team.model';



type SelectableEntity = ITeam;

@Component({
	selector: 'jhi-match-preview-load',
	templateUrl: './match-preview-load.component.html',
})
export class MatchPreviewLoadComponent {


	teamId?: number;
	tempMatchPreview?: any;	

	loadForm = this.fb.group({

		teamId: [],

	});

	constructor(

		protected matchPreviewService: MatchPreviewService,
		private fb: FormBuilder

	) { }



	trackById(index: number, item: SelectableEntity): any {
		return item.id;
	}

	loadByTeam(): void{
		this.teamId = this.getTeam();		
		this.matchPreviewService.load(this.teamId)
			.subscribe(
				response => {
					this.tempMatchPreview = response;
				}
			);
	}
	
	 private getTeam(): number {
		let team = 0;
		team = this.loadForm.get(['teamId'])!.value;
		
		return team;
	}

	previousState(): void {
		window.history.back();
	}

}
