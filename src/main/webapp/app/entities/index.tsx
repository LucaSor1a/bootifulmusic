import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Artist from './artist';
import Genre from './genre';
import Track from './track';
import Album from './album';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}artist`} component={Artist} />
      <ErrorBoundaryRoute path={`${match.url}genre`} component={Genre} />
      <ErrorBoundaryRoute path={`${match.url}track`} component={Track} />
      <ErrorBoundaryRoute path={`${match.url}album`} component={Album} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
