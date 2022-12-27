export type MediaDTO = {
  mediaId: string | undefined;
  mediaKey: string | undefined;
};

export class Media  {
  mediaId!: string;
  mediaUrl!: string;
  mediaType!: string;
  mediaKey!: string;
}
