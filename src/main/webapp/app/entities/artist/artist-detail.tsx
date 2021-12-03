import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './artist.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ArtistDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const artistEntity = useAppSelector(state => state.artist.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="artistDetailsHeading">
          <Translate contentKey="bootifulmusicApp.artist.detail.title">Artist</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{artistEntity.id}</dd>
          <dt>
            <span id="nick">
              <Translate contentKey="bootifulmusicApp.artist.nick">Nick</Translate>
            </span>
          </dt>
          <dd>{artistEntity.nick}</dd>
          <dt>
            <span id="firstName">
              <Translate contentKey="bootifulmusicApp.artist.firstName">First Name</Translate>
            </span>
          </dt>
          <dd>{artistEntity.firstName}</dd>
          <dt>
            <span id="lastName">
              <Translate contentKey="bootifulmusicApp.artist.lastName">Last Name</Translate>
            </span>
          </dt>
          <dd>{artistEntity.lastName}</dd>
        </dl>
        <Button tag={Link} to="/artist" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/artist/${artistEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ArtistDetail;
