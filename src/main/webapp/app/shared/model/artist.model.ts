export interface IArtist {
  id?: number;
  nick?: string;
  firstName?: string | null;
  lastName?: string | null;
}

export const defaultValue: Readonly<IArtist> = {};
