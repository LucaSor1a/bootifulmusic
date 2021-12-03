import { entityItemSelector } from '../../support/commands';
import {
  entityTableSelector,
  entityDetailsButtonSelector,
  entityDetailsBackButtonSelector,
  entityCreateButtonSelector,
  entityCreateSaveButtonSelector,
  entityCreateCancelButtonSelector,
  entityEditButtonSelector,
  entityDeleteButtonSelector,
  entityConfirmDeleteButtonSelector,
} from '../../support/entity';

describe('Track e2e test', () => {
  const trackPageUrl = '/track';
  const trackPageUrlPattern = new RegExp('/track(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'admin';
  const password = Cypress.env('E2E_PASSWORD') ?? 'admin';
  const trackSample = { name: 'parsing Vatu' };

  let track: any;

  before(() => {
    cy.window().then(win => {
      win.sessionStorage.clear();
    });
    cy.visit('');
    cy.login(username, password);
    cy.get(entityItemSelector).should('exist');
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/tracks+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/tracks').as('postEntityRequest');
    cy.intercept('DELETE', '/api/tracks/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (track) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/tracks/${track.id}`,
      }).then(() => {
        track = undefined;
      });
    }
  });

  it('Tracks menu should load Tracks page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('track');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Track').should('exist');
    cy.url().should('match', trackPageUrlPattern);
  });

  describe('Track page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(trackPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Track page', () => {
        cy.get(entityCreateButtonSelector).click({ force: true });
        cy.url().should('match', new RegExp('/track/new$'));
        cy.getEntityCreateUpdateHeading('Track');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click({ force: true });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', trackPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/tracks',
          body: trackSample,
        }).then(({ body }) => {
          track = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/tracks+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [track],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(trackPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Track page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('track');
        cy.get(entityDetailsBackButtonSelector).click({ force: true });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', trackPageUrlPattern);
      });

      it('edit button click should load edit Track page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Track');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click({ force: true });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', trackPageUrlPattern);
      });

      it('last delete button click should delete instance of Track', () => {
        cy.intercept('GET', '/api/tracks/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('track').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click({ force: true });
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', trackPageUrlPattern);

        track = undefined;
      });
    });
  });

  describe('new Track page', () => {
    beforeEach(() => {
      cy.visit(`${trackPageUrl}`);
      cy.get(entityCreateButtonSelector).click({ force: true });
      cy.getEntityCreateUpdateHeading('Track');
    });

    it('should create an instance of Track', () => {
      cy.get(`[data-cy="name"]`).type('Oregon Handcrafted').should('have.value', 'Oregon Handcrafted');

      cy.get(`[data-cy="released"]`).type('2021-12-02').should('have.value', '2021-12-02');

      cy.get(`[data-cy="length"]`).type('50768').should('have.value', '50768');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        track = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', trackPageUrlPattern);
    });
  });
});
