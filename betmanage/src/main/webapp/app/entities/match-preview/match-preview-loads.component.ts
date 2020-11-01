import { Component } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder } from '@angular/forms';
import { MatchPreviewService } from './match-preview.service';
import { ITeam } from 'app/shared/model/team.model';



type SelectableEntity = ITeam;

@Component({
	selector: 'jhi-match-preview-loads',
	templateUrl: './match-preview-loads.component.html',
})
export class MatchPreviewLoadsComponent {


	date?: Date;
	tempMatchPreview?: any;	

	loadForm = this.fb.group({

		date: [],

	});

	constructor(

		protected matchPreviewService: MatchPreviewService,
		private fb: FormBuilder

	) { }



	trackById(index: number, item: SelectableEntity): any {
		return item.id;
	}

	loadByDate(): void{
		this.date = this.getDate();		
		this.matchPreviewService.loads(this.date)
			.subscribe(
				response => {
					this.tempMatchPreview = response;
				}
			);
	}
	
	 private getDate(): Date {
		let date = new Date();
		date = this.loadForm.get(['date'])!.value;
		
		return date;
	}

	previousState(): void {
		window.history.back();
	}

}
