import { bootstrapApplication } from '@angular/platform-browser';
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { importProvidersFrom } from '@angular/core';
import { provideRouter } from '@angular/router';
import { AppComponent } from './app/app.component';
import { routes } from './app/app.routes';
import { AuthGuard } from './app/guard/auth.guard';
import { FormsModule } from '@angular/forms';

bootstrapApplication(AppComponent, {
  providers: [
    provideRouter(routes), // Configura as rotas sem precisar de app.module.ts
    provideHttpClient(),   // Permite chamadas HTTP no Angular
    importProvidersFrom(FormsModule), // Suporte ao [(ngModel)]
    AuthGuard             // Adiciona o AuthGuard para proteção de rotas
  ]
}).catch(err => console.error(err));
