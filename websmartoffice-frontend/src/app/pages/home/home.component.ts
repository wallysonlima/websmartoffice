import { Component, OnInit } from '@angular/core';
import { AuthService } from './../../services/auth.service';
import { Router } from '@angular/router';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';

interface PropertyResponseDTO {
  cpfProperty: string;
  registerProperty: string;
  notarialDeed: string;
  price: number;
  size: string;
}

@Component({
  selector: 'app-home',
  standalone: true, // ✅ Mantendo como Standalone
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
  imports: [CommonModule, RouterModule] // ✅ Importando RouterModule para usar <router-outlet>
})
export class HomeComponent implements OnInit {
  userSession: any = null;
  properties: PropertyResponseDTO[] = [];

  
  constructor(private authService: AuthService, private router: Router, private http: HttpClient) {}

  ngOnInit(): void {
    console.log('Aplicação iniciada!'); // ✅ Debug para ver se o componente está sendo carregado
    const session = localStorage.getItem('userSession');
    if ( session ) {
      this.userSession = JSON.parse(session);
      this.fetchProperties(this.userSession.email)
    }
  }

  fetchProperties(email: string) {
    this.http.get<PropertyResponseDTO[]>(`http://localhost:8080/user/getPropertiesFromUser?email=${email}`, {
      withCredentials: true
    }).subscribe({
      next: (data) => this.properties = data,
      error: (err) => console.error('Erro ao buscar propriedades:', err)
    });
  }

  logout() {
    this.authService.logout().subscribe(() => {
      this.router.navigate(['/login']); // ✅ Redireciona para login ao sair
    });
  }
}
