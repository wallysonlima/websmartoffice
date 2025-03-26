import { Component, OnInit } from '@angular/core';
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
  address: string;
}

interface ContractRequestDTO {
  cpfBuyer: string;
  cpfSeller: string;
  privateKeyFromBankAccount: string;
  address: string;
  propertySize: string;
  priceInBrl: number;
  registerProperty: string;
  notarialDeed: string;
}

interface BuyPropertyRequestDTO {
  cpfBuyer: string;
  cpfSeller: string;
  registerProperty: string;
  buyerPrivateKey: string;
  contractAddress: string;
}

var contractAddressResponse: string = '';

@Component({
  selector: 'app-buy',
  standalone: true,
  templateUrl: './buy.component.html',
  styleUrl: './buy.component.scss',
  imports: [CommonModule, RouterModule]
})
export class BuyComponent implements OnInit {
  userSession: any = null;
  properties: PropertyResponseDTO[] = [];
  propertyStates: { signed: boolean; paid: boolean; successMessage?: string }[] = [];

  constructor(private router: Router, private http: HttpClient) {}

  ngOnInit(): void {
    console.log('Aplicação iniciada!'); // ✅ Debug para ver se o componente está sendo carregado
    const session = localStorage.getItem('userSession');
    if ( session ) {
      this.userSession = JSON.parse(session);
      this.fetchAllProperties()
    }
  }

  fetchAllProperties() {
    this.http.get<PropertyResponseDTO[]>(`http://localhost:8080/user/getProperties`, {
      withCredentials: true
    }).subscribe({
      next: (data) => {
        const userCpf = this.userSession?.cpf;
        this.properties = data.filter(property => property.cpfProperty !== userCpf);
        this.propertyStates = this.properties.map(() => ({ signed: false, paid: false }));
      },
      error: (err) => console.error('Erro ao buscar propriedades:', err)
    });
  }

  confirmSign(index: number, property: PropertyResponseDTO) {
    const confirmed = window.confirm("Realmente deseja assinar o contrato desta propriedade ?");
    if (!confirmed) return;

    const payload: ContractRequestDTO = {
      cpfBuyer: this.userSession.cpf,
      cpfSeller: property.cpfProperty,
      privateKeyFromBankAccount: this.userSession.bankAccount.privateKey,
      address: property.address,
      propertySize: property.size,
      priceInBrl: property.price,
      registerProperty: property.registerProperty,
      notarialDeed: property.notarialDeed
    };
    
    this.http.post('http://localhost:8080/user/property/signContract', payload, {
      withCredentials: true,
      responseType: 'text'
    }).subscribe({
      next: (contractAddress) => {
        this.propertyStates[index].signed = true;
        this.propertyStates[index].successMessage = "Contrato assinado com sucesso!";
        contractAddressResponse = contractAddress;
        this.autoClearMessage(index);
      },
      error: (err) => console.error('Erro ao assinar contrato:', err)
    });
  }

  confirmPayment(index: number, property: PropertyResponseDTO) {
    const confirmed = window.confirm("Realmente deseja comprar esta propriedade ?.");
    if (!confirmed) return;

    const payload: BuyPropertyRequestDTO = {
      cpfBuyer: this.userSession.cpf,
      cpfSeller: property.cpfProperty,
      registerProperty: property.registerProperty,
      buyerPrivateKey: this.userSession.bankAccount.privateKey,
      contractAddress: contractAddressResponse
    };

    this.http.post('http://localhost:8080/user/property/buyProperty', payload, {
      withCredentials: true,
      responseType: 'text'
    }).subscribe({
      next: () => {
        this.propertyStates[index].paid = true;
        this.propertyStates[index].successMessage = "Pagamento realizado com sucesso!";
        this.autoClearMessage(index);
      },
      error: (err) => console.error('Erro ao realizar pagamento:', err)
    });
  }

  autoClearMessage(index: number) {
    setTimeout(() => {
      this.propertyStates[index].successMessage = '';
    }, 3000);
  }
}