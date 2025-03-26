// src/app/services/user.service.ts
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface BankAccountResponseDTO {
  bankCpf: string;
  privateKey: string;
  ethAddress: string;
  balance: number;
}

export interface PersonResponseDTO {
  cpf: string;
  rg: string;
  name: string;
  email: string;
  phoneNumber: string;
  dateBirth: string;
  gender: string;
  civilState: string;
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

  getBalance(cpf: string): Observable<number> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });
    const params = new HttpParams().set('cpf', cpf);

    return this.http.get<number>(`${this.apiUrl}/balance`, { params, headers, withCredentials: true });
  }
}
