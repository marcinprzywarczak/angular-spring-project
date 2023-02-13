import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ListApiService } from '../../shared/services/list-api.service';
import { ToDoList } from '../../shared/models/toDoList';
import { Subscription } from 'rxjs';
import { DataReloadTriggerService } from '../../shared/services/data-reload-trigger.service';

@Component({
  selector: 'app-list-details',
  templateUrl: './list-details.component.html',
  styleUrls: ['./list-details.component.scss'],
})
export class ListDetailsComponent implements OnInit, OnDestroy {
  listId: number;
  list: ToDoList;
  newItem: string;
  reloadItemsSubscription: Subscription;

  constructor(
    private route: ActivatedRoute,
    private listApiService: ListApiService,
    private dataReloadTriggerService: DataReloadTriggerService
  ) {}

  ngOnInit(): void {
    if (this.route.snapshot.paramMap.get('id'))
      this.listId = Number(this.route.snapshot.paramMap.get('id'));

    this.listenOnReload();

    this.getList();
  }

  ngOnDestroy() {
    this.reloadItemsSubscription.unsubscribe();
  }

  listenOnReload() {
    this.reloadItemsSubscription =
      this.dataReloadTriggerService.listItemsTrigger.subscribe(() => {
        this.getList();
      });
  }

  addNewItem() {
    if (!this.newItem) return;
    this.listApiService
      .addNewItem({ name: this.newItem, isDone: false }, this.list.id)
      .subscribe((res) => {
        this.getList();
        this.newItem = '';
      });
  }

  checkAsDone(id: number, done: boolean) {
    this.listApiService.checkItemAsDone(id, done).subscribe((res) => {
      console.log(res);
      this.getList();
    });
  }

  deleteItem(id: number) {
    this.listApiService.deleteItem(id).subscribe((res) => {
      this.getList();
    });
  }

  getList() {
    this.listApiService.getList(this.listId).subscribe((res) => {
      this.list = res;
      console.log(res);
    });
  }
}
