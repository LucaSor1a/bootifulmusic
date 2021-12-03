import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IAlbum } from 'app/shared/model/album.model';
import { getEntities as getAlbums } from 'app/entities/album/album.reducer';
import { getEntity, updateEntity, createEntity, reset } from './track.reducer';
import { ITrack } from 'app/shared/model/track.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const TrackUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const albums = useAppSelector(state => state.album.entities);
  const trackEntity = useAppSelector(state => state.track.entity);
  const loading = useAppSelector(state => state.track.loading);
  const updating = useAppSelector(state => state.track.updating);
  const updateSuccess = useAppSelector(state => state.track.updateSuccess);
  const handleClose = () => {
    props.history.push('/track' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getAlbums({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...trackEntity,
      ...values,
      album: albums.find(it => it.id.toString() === values.album.toString()),
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
          ...trackEntity,
          album: trackEntity?.album?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="bootifulmusicApp.track.home.createOrEditLabel" data-cy="TrackCreateUpdateHeading">
            <Translate contentKey="bootifulmusicApp.track.home.createOrEditLabel">Create or edit a Track</Translate>
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
                  id="track-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('bootifulmusicApp.track.name')}
                id="track-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('bootifulmusicApp.track.released')}
                id="track-released"
                name="released"
                data-cy="released"
                type="date"
              />
              <ValidatedField
                label={translate('bootifulmusicApp.track.length')}
                id="track-length"
                name="length"
                data-cy="length"
                type="text"
              />
              <ValidatedField id="track-album" name="album" data-cy="album" label={translate('bootifulmusicApp.track.album')} type="select">
                <option value="" key="0" />
                {albums
                  ? albums.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.name}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/track" replace color="info">
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

export default TrackUpdate;
