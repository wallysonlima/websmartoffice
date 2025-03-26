import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PersonService {
  private apiUrl = 'http://localhost:8080/admin/register'; // 🔹 Ajuste conforme necessário

  constructor(private http: HttpClient) {}

  register(personData: any): Observable<any> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });
  
    return this.http.post(`${this.apiUrl}/person`, personData, {
      headers,
      responseType: 'text' as 'json',  // 👈 Isso força a tratar como texto
      withCredentials: true            // 👈 Se você precisa de sessão/cookie
    });
  }

}
