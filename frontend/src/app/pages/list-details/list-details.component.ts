import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ListApiService } from '../../shared/services/list-api.service';
import { ToDoList } from '../../shared/models/toDoList';

@Component({
  selector: 'app-list-details',
  templateUrl: './list-details.component.html',
  styleUrls: ['./list-details.component.scss'],
})
export class ListDetailsComponent implements OnInit {
  listId: number;
  list: ToDoList;
  newItem: string;
  constructor(
    private route: ActivatedRoute,
    private listApiService: ListApiService
  ) {}

  ngOnInit(): void {
    if (this.route.snapshot.paramMap.get('id'))
      this.listId = Number(this.route.snapshot.paramMap.get('id'));
    console.log(this.listId);

    this.getList();
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
