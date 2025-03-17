import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, map } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/auth';

  constructor(private http: HttpClient) {}

  login(credentials: { email: string; password: string }): Observable<any> {
    return this.http.post(`${this.apiUrl}/login`, credentials, {
      withCredentials: true, // Permite o uso de cookies para autenticação baseada em sessão
      headers: new HttpHeaders({ 'Content-Type': 'application/json' }) // Garante o formato correto
    });
  }

  logout(): Observable<any> {
    return this.http.post(`${this.apiUrl}/logout`, {}, {
        withCredentials: true, // Permite o uso de cookies para autenticação baseada em sessão
        headers: new HttpHeaders({ 'Content-Type': 'application/json' }) // Garante o formato correto
      });
  }

  getCurrentUser(): Observable<any> {
    return this.http.get(`${this.apiUrl}/me`, { withCredentials: true });
  }

  isAuthenticated(): Observable<boolean> {
    return this.getCurrentUser().pipe(
      map(user => !!user) // Se houver um usuário, retorna true, senão false
    );
  }
}

