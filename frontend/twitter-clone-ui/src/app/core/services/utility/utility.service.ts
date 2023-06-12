import { Injectable } from '@angular/core';
import { Comment, Tweet } from 'src/app/shared/models/tweet';

@Injectable({
  providedIn: 'root',
})
export class UtilityService {
  public isWithinView: boolean = false;

  constructor() {}

  public capitaliseAndSpace(text: string) {
    const firstCharacter = text.charAt(0).toUpperCase();
    const remainingText = text.replace(/(?=[A-Z])/g, ' ').slice(1);
    return firstCharacter + remainingText;
  }

  public getDateSinceCreation(tweet: Tweet | Comment): string {
    const oneHour: number = 60 * 60 * 1000;
    const oneDay: number = 24 * oneHour;
    const oneWeek: number = 7 * oneDay;
    const oneMonth: number = 30 * oneDay;
    const oneYear: number = 365 * oneDay;

    const currentDate = Date.now();
    const tweetDate = new Date(tweet.dateOfCreation).getTime();

    const differenceInMillis = Math.abs(currentDate - tweetDate);

    if (differenceInMillis < oneDay) {
      const differenceInHours = Math.floor(differenceInMillis / oneHour);
      return differenceInHours === 1
        ? `${differenceInHours} hour ago`
        : `${differenceInHours} hours ago`;
    } else if (differenceInMillis < oneWeek) {
      const differenceInDays = Math.floor(differenceInMillis / oneDay);
      return differenceInDays === 1
        ? `${differenceInDays} day ago`
        : `${differenceInDays} days ago`;
    } else if (differenceInMillis < oneMonth) {
      const differenceInWeeks = Math.floor(differenceInMillis / oneWeek);
      return differenceInWeeks === 1
        ? `${differenceInWeeks} week ago`
        : `${differenceInWeeks} weeks ago`;
    } else if (differenceInMillis < oneYear) {
      const differenceInMonths = Math.floor(differenceInMillis / oneMonth);
      return differenceInMonths === 1
        ? `${differenceInMonths} month ago`
        : `${differenceInMonths} months ago`;
    } else {
      return '';
    }
  }

  public setIsWithinView(value: boolean) {
    this.isWithinView = value;
  }
}
