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
    const headers = new HttpHeaders({
        'Content-Type': 'application/json' // ✅ Define o Content-Type como JSON
    });
    
    return new Observable(observer => {
      this.http.post(`${this.apiUrl}/login`, credentials, {headers}).subscribe({
        next: (response: any) => {
          localStorage.setItem('userEmail', response.email); // ✅ Armazena email do usuário
          localStorage.setItem('userRole', JSON.stringify(response.roles)); // ✅ Armazena roles
          observer.next(response);
          observer.complete();
        },
        error: (error) => {
          observer.error(error);
        }
      });
    });
  }

  logout(): Observable<any> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    localStorage.removeItem('userEmail');
    localStorage.removeItem('userRole');
    return this.http.post('http://localhost:8080/auth/logout', {}, {
      withCredentials: true,
      responseType: 'text'  // 👈 isso é o que corrige o erro de parsing!
    });
  }

  getUserRole(): string[] {
    return JSON.parse(localStorage.getItem('userRole') || '[]'); // ✅ Obtém a role do usuário
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

