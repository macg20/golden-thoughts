import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class ConfigService {

  url: string = 'https://goldenthoughts.herokuapp.com/goldenthoughts/admin/buffer?page=0&size=10'
  // url: string = 'http://localhost:8080/goldenthoughts/admin/buffer?page=0&size=10'

  constructor(private http: HttpClient) {
  }

  getAdminGoldenThoughts() {
    return this.http.get(this.url);
  }
}
