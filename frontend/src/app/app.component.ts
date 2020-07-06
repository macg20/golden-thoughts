import {Component} from '@angular/core';
import {ConfigService} from "./config.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  providers: [
    ConfigService
  ]
})
export class AppComponent {
  title = 'frontend';


  constructor(private configService: ConfigService) {
    this.showGoldenThoughts();
  }

  showGoldenThoughts() {
    this.configService.getAdminGoldenThoughts()
      .subscribe(
        response => this.handleSuccessfulResponse(response),
        error => this.handleErrorResponse(error)
      );
  }

  handleSuccessfulResponse(response){
    console.log(response);
    console.log(response.message);
  }

  handleErrorResponse(error) {
    console.log(error);
    console.log(error.error);
    console.log(error.error.message);

  }
}
