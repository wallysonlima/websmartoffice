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
  isAdmin = false; // ✅ Define se o usuário é admin

  
  constructor(private authService: AuthService, private router: Router, private http: HttpClient) {
    this.loadSession();
    this.checkUserRole();
  }

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

  formatCpf(cpf: string): string {
    return cpf.replace(/(\d{3})(\d{3})(\d{3})(\d{2})/, '$1.$2.$3-$4');
  }
  
  formatDate(date: string): string {
    const d = new Date(date);
    return d.toLocaleDateString('pt-BR');
  }
  
  formatPhone(phone: string): string {
    return phone.replace(/(\d{2})(\d{5})(\d{4})/, '($1) $2-$3');
  }

  logout() {
    this.authService.logout().subscribe(() => {
      this.router.navigate(['/login']); // ✅ Redireciona para login ao sair
    });
  }

  loadSession() {
    const savedSession = localStorage.getItem('userSession');
    if (savedSession) {
      this.userSession = JSON.parse(savedSession);
      console.log('🔹 Sessão carregada:', this.userSession);
    }
  }

  checkUserRole() {
    const roles = this.authService.getUserRole();
    this.isAdmin = roles.includes('ROLE_ADMIN'); // ✅ Verifica se o usuário tem a role ADMIN
  }
}
