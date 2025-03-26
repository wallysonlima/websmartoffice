import { Component, OnInit} from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';

interface ContractResponseDTO {
  contractAddress: string;
  buyerCpf: string;
  sellerCpf: string;
  priceInBrl: number;
  registerProperty: string;
  notarialDeed: string;
  dateCreated: string;
}

@Component({
  selector: 'app-contracts',
  standalone: true,
  templateUrl: './contracts.component.html',
  styleUrl: './contracts.component.scss',
  imports: [CommonModule, RouterModule] // ✅ Importando RouterModule para usar <router-outlet>
})
export class ContractsComponent implements OnInit {
  userSession: any = null;
  contracts: ContractResponseDTO[] = [];

   constructor(private http: HttpClient) {}

   ngOnInit(): void {
    console.log('Aplicação iniciada!'); // ✅ Debug para ver se o componente está sendo carregado
    const session = localStorage.getItem('userSession');
    if ( session ) {
      this.userSession = JSON.parse(session);
      this.fetchContracts()
    }
  }

  fetchContracts() {
    this.http.get<ContractResponseDTO[]>('http://localhost:8080/admin/getContracts', {
      withCredentials: true
    }).subscribe({
      next: (data) => this.contracts = data,
      error: (err) => console.error('Erro ao buscar contratos:', err)
    });
  }
}
