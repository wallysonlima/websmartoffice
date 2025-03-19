import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PropertyService {
  private apiUrl = 'http://localhost:8080/admin/register'; // 🔹 Ajuste conforme necessário

  constructor(private http: HttpClient) {}

  register(propertyData: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/property`, propertyData);
  }
}
