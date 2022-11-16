import { User } from './user';

export interface ConfirmationToken {
  tokenId: string;
  token: string;
  CreatedDate: Date;
  createdAt: Date;
  expiresAt: Date;
  confirmedAt: Date;
  user: User;
}
