import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { IMatchPreview } from 'app/shared/model/match-preview.model';
import { MatchPreviewService } from './match-preview.service';
import { MatchPreviewSendDialogComponent } from './match-preview-send-dialog.component';

@Component({
	selector: 'jhi-match-preview-detail',
	templateUrl: './match-preview-detail.component.html',
})
export class MatchPreviewDetailComponent implements OnInit {
	matchPreview: IMatchPreview | null = null;

	constructor(
		protected dataUtils: JhiDataUtils, 
		protected activatedRoute: ActivatedRoute, 
		protected matchPreviewService: MatchPreviewService, 
		protected modalService: NgbModal) { }

	ngOnInit(): void {
		this.activatedRoute.data.subscribe(({ matchPreview }) => (this.matchPreview = matchPreview));
	}

	byteSize(base64String: string): string {
		return this.dataUtils.byteSize(base64String);
	}

	openFile(contentType = '', base64String: string): void {
		this.dataUtils.openFile(contentType, base64String);
	}

	previousState(): void {
		window.history.back();
	}

	sendToBetPreview(matchPreview: IMatchPreview): void {
		const modalRef = this.modalService.open(MatchPreviewSendDialogComponent, { size: 'lg', backdrop: 'static' });
		modalRef.componentInstance.matchPreview = matchPreview;
	}
}
