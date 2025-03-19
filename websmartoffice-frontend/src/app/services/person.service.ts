import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PersonService {
  private apiUrl = 'http://localhost:8080/api/admin/person'; // 🔹 Ajuste conforme necessário

  constructor(private http: HttpClient) {}

  register(personData: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/register`, personData);
  }
}
