// src/app/services/user.service.ts
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface BankAccountResponseDTO {
  bankCpf: string;
  privateKey: string;
  ethAddress: string;
  balance: number;
}

export interface PersonResponseDTO {
  cpf: string;
  name: string;
  email: string;
  bankAccount?: BankAccountResponseDTO;
  role: string;
}

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl = 'http://localhost:8080/user';

  constructor(private http: HttpClient) {}

  getUserByEmail(email: string): Observable<PersonResponseDTO> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });

    return this.http.get<PersonResponseDTO>(`${this.apiUrl}/getUser?email=${email}`, {
      headers,
      withCredentials: true
    });
  }
}
