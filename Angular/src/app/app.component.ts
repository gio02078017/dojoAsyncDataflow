import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { AsyncClientService } from './services/async-client.service';
import { BusinessService } from './services/business.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {



  title = 'front-async-angular';
  delay = 1000;
  user_ref = "unique_id";
  event_recived: Subscription | undefined;
  results: any = [];
  private eventRecived: Subscription | undefined;

  constructor(
    private asyncClientService: AsyncClientService,
    private businessService: BusinessService
  ) { }

  ngOnInit(){
    //localStorage.setItem("inicial", "inicial");
    this.asyncClientService.getCredentials(this.user_ref);
    this.listenEvents();
  }

  generateRequest() {
    /*let start: any = performance.now(); 
    //var message: string = `Get empty response after ${performance.now()- start} ms`;
    this.businessServices.callBusinessUseCase(this.delay).subscribe((res: any) => this.result.push(`Get empty response after ${performance.now() - start} ms`));*/
    console.log("delay ", this.delay);
    let start = performance.now();
    const businessResponse = this.businessService.callBusinessUseCase(this.delay);
    console.log(`Get empty response after ${performance.now() - start} ms`);
    this.results.push(`Get empty response after ${performance.now() - start} ms`);
    /*this.businessService
      .callBusinessUseCase(this.delay);
      .subscribe((res: any) => {
        this.results.push(
          `Get empty response after ${performance.now() - start} ms`
        );
      });*/
  }

  private listenEvents() {
    this.event_recived = this.asyncClientService.eventRecived.subscribe(
      (message: any) => {
        /* if (msg.event == 'businessEvent') {
           this.results.push(
             `Message from async dataflow, title: ${msg.payload.title} detail: ${msg.payload.detail}`
           );
         }*/
        console.log("mensaje ",message);
        if (message.event == 'businessEvent') {
          this.results.push(`Message from Async after DataFlow, title: ${message.payload.messageData.title} detail: ${message.payload.messageData.detail}`);
        }
      }
    );
  }


}
