import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IArtist } from 'app/shared/model/artist.model';
import { getEntities as getArtists } from 'app/entities/artist/artist.reducer';
import { IGenre } from 'app/shared/model/genre.model';
import { getEntities as getGenres } from 'app/entities/genre/genre.reducer';
import { getEntity, updateEntity, createEntity, reset } from './album.reducer';
import { IAlbum } from 'app/shared/model/album.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const AlbumUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const artists = useAppSelector(state => state.artist.entities);
  const genres = useAppSelector(state => state.genre.entities);
  const albumEntity = useAppSelector(state => state.album.entity);
  const loading = useAppSelector(state => state.album.loading);
  const updating = useAppSelector(state => state.album.updating);
  const updateSuccess = useAppSelector(state => state.album.updateSuccess);
  const handleClose = () => {
    props.history.push('/album' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getArtists({}));
    dispatch(getGenres({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...albumEntity,
      ...values,
      artist: artists.find(it => it.id.toString() === values.artist.toString()),
      genre: genres.find(it => it.id.toString() === values.genre.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...albumEntity,
          artist: albumEntity?.artist?.id,
          genre: albumEntity?.genre?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="bootifulmusicApp.album.home.createOrEditLabel" data-cy="AlbumCreateUpdateHeading">
            <Translate contentKey="bootifulmusicApp.album.home.createOrEditLabel">Create or edit a Album</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="album-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('bootifulmusicApp.album.name')}
                id="album-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('bootifulmusicApp.album.released')}
                id="album-released"
                name="released"
                data-cy="released"
                type="date"
              />
              <ValidatedField
                id="album-artist"
                name="artist"
                data-cy="artist"
                label={translate('bootifulmusicApp.album.artist')}
                type="select"
              >
                <option value="" key="0" />
                {artists
                  ? artists.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.nick}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="album-genre" name="genre" data-cy="genre" label={translate('bootifulmusicApp.album.genre')} type="select">
                <option value="" key="0" />
                {genres
                  ? genres.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.name}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/album" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default AlbumUpdate;
