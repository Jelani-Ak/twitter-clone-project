import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';

@Injectable({
  providedIn: 'root',
})
export class SnackbarService {
  constructor(private snackbar: MatSnackBar) {}

  public displayToast(message: string, context: string) {
    this.snackbar.open(message, context, { duration: 2500 });
  }
}
