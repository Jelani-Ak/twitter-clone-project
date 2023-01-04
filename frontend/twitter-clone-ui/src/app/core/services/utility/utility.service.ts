import { Injectable } from '@angular/core';
import { Comment, Tweet } from 'src/app/shared/models/tweet';

@Injectable({
  providedIn: 'root',
})
export class UtilityService {
  constructor() {}

  public capitaliseAndSpace(text: string) {
    const firstCharacter = text.charAt(0).toUpperCase();
    const remainingText = text.replace(/(?=[A-Z])/g, " ").slice(1);
    return firstCharacter + remainingText;
  }

  public getDateSinceCreation(tweet: Tweet | Comment): string {
    const currentDate = new Date();
    const tweetDate = new Date(tweet.dateOfCreation);

    const differenceInHours =
      Math.abs(tweetDate.getTime() - currentDate.getTime()) / 36e5;

    const lessThanADay = differenceInHours < 24;
    if (lessThanADay) {
      return differenceInHours == 1
        ? `${differenceInHours.toFixed(0)} hour ago`
        : `${differenceInHours.toFixed(0)} hours ago`;
    }

    const olderThanADay = differenceInHours >= 24 && differenceInHours < 168;
    if (olderThanADay) {
      const differenceInDays = Math.ceil(
        Math.abs(tweetDate.getTime() - currentDate.getTime()) /
          (1000 * 60 * 60 * 24)
      );

      return differenceInDays == 1
        ? `${differenceInDays.toFixed(0)} day ago`
        : `${differenceInDays.toFixed(0)} days ago`;
    }

    const olderThanAWeek = differenceInHours >= 168 && differenceInHours < 730;
    if (olderThanAWeek) {
      const differenceInWeeks = Math.ceil(
        Math.abs(tweetDate.getTime() - currentDate.getTime()) /
          (1000 * 60 * 60 * 24 * 7)
      );

      return differenceInWeeks == 1
        ? `${differenceInWeeks.toFixed(0)} week ago`
        : `${differenceInWeeks.toFixed(0)} weeks ago`;
    }

    const olderThanAMonth = differenceInHours >= 730 && differenceInHours < 8_760;
    if (olderThanAMonth) {
      const differenceInMonths = Math.abs(
        (tweetDate.getMonth() - currentDate.getMonth()) +
        12 * (tweetDate.getFullYear() - currentDate.getFullYear())
      );

      return differenceInMonths == 1
        ? `${differenceInMonths.toFixed(0)} month ago`
        : `${differenceInMonths.toFixed(0)} months ago`;
    }

    return "";
  }
}
