import dayjs from 'dayjs';
import { IAlbum } from 'app/shared/model/album.model';

export interface ITrack {
  id?: number;
  name?: string;
  released?: string | null;
  length?: number | null;
  album?: IAlbum | null;
}

export const defaultValue: Readonly<ITrack> = {};
