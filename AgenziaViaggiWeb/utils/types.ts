export interface Pacchetto {
  id?: number;
  titolo: string;
  descrizione?: string;
  inizio?: string;
  fine?: string;
  tags?: string;
  prezzo: number;
  isOfferta: boolean;
  immagine?: string;
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
  inizio?: string;
  fine?: string;
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