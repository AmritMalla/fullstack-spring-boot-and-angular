import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { HttpClient, HttpResponse, HttpHeaders } from '@angular/common/http'
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { catchError } from 'rxjs/operators';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  private baseUrl: string = 'http://localhost:8080';
  public submitted: boolean;
  rooms: Room[];
  roomSearch: FormGroup;
  currentCheckInVal: string;
  currentCheckOutVal: string;

  constructor(private http: HttpClient) {

  }

  ngOnInit() {
    this.roomSearch = new FormGroup({
      checkin: new FormControl(''),
      checkout: new FormControl('')
    });
    // this.rooms = ROOMS;

    this.roomSearch.valueChanges
      .subscribe(
        valueChange => {
          this.currentCheckInVal = valueChange.checkin;
          this.currentCheckOutVal = valueChange.checkout;
        }
      );
  }


  onSubmit({ value, valid }: { value: RoomSearch, valid: boolean }) {
    this.getAll().subscribe(
      (data: Object) => { this.rooms = data['content'] },
      err => {
        console.log(err);
      }, () => {
        console.log("All Rooms list is retrieved");
      }
    );


  }

  reserveRoom(value: string) {
    this.createReservation(new ReserveRoomRequest(value,
      this.currentCheckInVal, this.currentCheckOutVal));
  }

  createReservation(body: ReserveRoomRequest) {
    let bodyString = JSON.stringify(body);
    let headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    let option = { headers: headers };
    this.http.post<any>(this.baseUrl + '/room/reservation/v1', bodyString, option).subscribe(
      response => console.log(response)
    )
  }

  getAll(): Observable<Room[]> {
    return this.http.get<Room[]>(this.baseUrl + '/room/reservation/v1?checkin='+this.currentCheckInVal+'&checkout='+this.currentCheckOutVal);
  }

  mapRoom(response: HttpResponse<Room>) {
    return response.body;

  }

}
export interface RoomSearch {
  checkin: String;
  checkout: String;
}
export interface Room {
  id: string;
  roomNumber: string;
  price: string;
  links: string;
}

export class ReserveRoomRequest {
  // roomId: string;
  // checkin: string;
  // checkout: string;
  constructor(private roomId: string,
    private checkin: string,
    private checkout: string) {

    // this.roomId = roomId;
    // this.checkin = checkin;
    // this.checkout = checkout;

  }
}
// var ROOMS: Room[] = [{
//   "id": "12345",
//   "roomNumber": "409",
//   "price": "46",
//   "links": ""
// },
// {
//   "id": "23344",
//   "roomNumber": "410",
//   "price": "89",
//   "links": ""
// },
// {
//   "id": "123745",
//   "roomNumber": "411",
//   "price": "123",
//   "links": ""
// },
// {
//   "id": "123545",
//   "roomNumber": "412",
//   "price": "25",
//   "links": ""
// }];

