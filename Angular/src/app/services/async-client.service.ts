//import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { AsyncClient } from '@bancolombia/chanjs-client';
import { Subject } from 'rxjs';
import { BusinessMessage } from '../models/businessMessage.interface';
import axios from 'axios';

@Injectable({
  providedIn: 'root'
})
export class AsyncClientService {

  constructor(/*private http: HttpClient*/) { }

  private chanjs_client: any;
  private getEventFromAsyncDataFlow = new Subject<BusinessMessage>();
  public eventRecived = this.getEventFromAsyncDataFlow.asObservable();

  public async getCredentials(user_ref: any) {
    //if (window.sessionStorage && (window.sessionStorage.getItem('channelRef') && window.sessionStorage.getItem('channelRef') != 'undefined') && (window.sessionStorage.getItem('channelSecret') && window.sessionStorage.getItem('channelSecret') != 'undefined')) {
    let control: Boolean = false;
    if (typeof window !== 'undefined' && typeof localStorage !== undefined) {
      console.log(localStorage.length > 0)
      if (localStorage.length > 0 && sessionStorage.getItem('channelRef') && sessionStorage.getItem('channelRef')) {
        this.initChannel();
        control = true;
      } else {
        this.callService(user_ref);
        control = true;
      }
      /*if(sessionStorage.getItem('chan nelRef') && sessionStorage.getItem('channelSecret')){
        this.initChannel();
      }*/

    } else {
      this.callService(user_ref);
      control = true;
    }
    return control;
  }

  private async callService(user_ref: any) {
    const url = `${environment.api_business}/credentials`;
    const response = await axios.get(url, { params: { user_ref: user_ref } });
    console.log("response", response);
    if (response != null) {
      const channelRef = response.data.channelRef;
      const channelSecret = response.data.channelSecret;
      console.log('channelRef ', channelRef);
      console.log('channelSecret ', channelSecret);
      localStorage.setItem('channelRef', channelRef);
      localStorage.setItem('channelSecret', channelSecret);
      console.log('storage', localStorage);
      this.initChannel();
    }
    /*this.http.get(url, { params: { user_ref: user_ref } })
      .subscribe((res: any) => {
        sessionStorage.setItem('channel_ref', res.channel_ref);
        sessionStorage.setItem('channel_secret', res.channel_secret);
        this.initChannel()
      })*/
  }

  private initChannel() {
    console.log('localStorage initChannel', localStorage);
    const channelRef: any = localStorage.getItem('channelRef');
    const channelSecret: any = localStorage.getItem('channelSecret');
    console.log('channelRef initChannel', channelRef);
    console.log('channelSecret initChannel', channelSecret);
    if (channelRef && channelSecret) {
      console.log("ws:socket");
      this.chanjs_client = new AsyncClient({
        socket_url: `${environment.socket_url}/ext/socket`,
        channel_ref: channelRef,
        channel_secret: channelSecret
      });
      this.chanjs_client.connect();
      this.chanjs_client.listenEvent('businessEvent', (message: BusinessMessage) =>
        this.getEventFromAsyncDataFlow.next(message)
      )
    }

  }
}
