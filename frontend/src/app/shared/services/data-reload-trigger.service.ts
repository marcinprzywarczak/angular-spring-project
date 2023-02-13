import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class DataReloadTriggerService {
  userReloadTrigger = new Subject<boolean>();
  listReloadTrigger = new Subject<boolean>();
  listItemsTrigger = new Subject<boolean>();
  constructor() {}

  triggerUserReload() {
    this.userReloadTrigger.next(true);
  }

  triggerListReload() {
    this.listReloadTrigger.next(true);
  }

  triggerListItemsReload() {
    this.listItemsTrigger.next(true);
  }
}
