import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './track.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const TrackDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const trackEntity = useAppSelector(state => state.track.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="trackDetailsHeading">
          <Translate contentKey="bootifulmusicApp.track.detail.title">Track</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{trackEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="bootifulmusicApp.track.name">Name</Translate>
            </span>
          </dt>
          <dd>{trackEntity.name}</dd>
          <dt>
            <span id="released">
              <Translate contentKey="bootifulmusicApp.track.released">Released</Translate>
            </span>
          </dt>
          <dd>{trackEntity.released ? <TextFormat value={trackEntity.released} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="length">
              <Translate contentKey="bootifulmusicApp.track.length">Length</Translate>
            </span>
          </dt>
          <dd>{trackEntity.length}</dd>
          <dt>
            <Translate contentKey="bootifulmusicApp.track.album">Album</Translate>
          </dt>
          <dd>{trackEntity.album ? trackEntity.album.name : ''}</dd>
        </dl>
        <Button tag={Link} to="/track" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/track/${trackEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TrackDetail;
