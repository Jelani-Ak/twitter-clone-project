import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';

@Injectable({
  providedIn: 'root',
})
export class SnackbarService {
  constructor(private snackbar: MatSnackBar) {}

  public displayToast(message: string, context: string | undefined = undefined) {
    this.snackbar.open(message, context ?? 'Ok', { duration: 2500 });
  }
}
