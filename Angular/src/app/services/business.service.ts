//import { HttpClient, HttpParams} from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Injectable } from '@angular/core';
import axios from 'axios';

@Injectable({
  providedIn: 'root'
})
export class BusinessService {

  constructor(/*private http: HttpClient*/) {}

  public async callBusinessUseCase(delay: number){
    const url =  `${environment.api_business}/business`; 
    const channelRef: any = localStorage.getItem('channelRef');
    //const httpParams = new HttpParams().set('channel_ref', channelRef).set('delay', delay);
    const response = await axios.get(url,{params: {'channel_ref':channelRef, delay}});
    console.log("response callBusinessUseCase ", response); 
    return response.data;
    //return this.http.get(url,{params: httpParams});
  }
}
