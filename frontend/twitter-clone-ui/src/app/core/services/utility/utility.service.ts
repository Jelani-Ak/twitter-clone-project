import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class UtilityService {
  constructor() {}

  capitaliseAndSpace(text: string) {
    const firstCharacter = text.charAt(0).toUpperCase();
    const remainingText = text.replace(/(?=[A-Z])/g, " ").slice(1);
    return firstCharacter + remainingText;
  }
}
