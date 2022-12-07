import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class DataReloadTriggerService {
  userReloadTrigger = new Subject<boolean>();
  constructor() {}

  triggerUserReload() {
    this.userReloadTrigger.next(true);
  }
}
