import dayjs from 'dayjs';
import { ITrack } from 'app/shared/model/track.model';
import { IArtist } from 'app/shared/model/artist.model';
import { IGenre } from 'app/shared/model/genre.model';

export interface IAlbum {
  id?: number;
  name?: string;
  released?: string | null;
  tracks?: ITrack[] | null;
  artist?: IArtist | null;
  genre?: IGenre | null;
}

export const defaultValue: Readonly<IAlbum> = {};
