export interface Pacchetto {
  id?: number;
  titolo: string;
  descrizione?: string;
  tags?: string;
  prezzo: number;
  isOfferta: boolean;
  immagine?: string;
  servizi?: Servizio[];
}

export interface Cliente {
  id?: number;
  nome: string;
  email: string;
  preferenze?: string;
}

export interface Servizio {
  id?: number;
  idPacchetto: number;
  tipo?: string;
  nome: string;
  prezzo: number;
  descrizione?: string;
  immagine?: string;
}

export interface Prenotazione {
  id?: number;
  idCliente?: number;
  idPacchetto?: number;
  
  cliente?: Cliente;
  pacchetto?: Pacchetto;
  
  dataPrenotazione?: string;
  prezzoPagato: number;
  stato: string; 
}