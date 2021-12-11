describe('/admin', () => {
  before(() => {
    cy.window().then(win => {
      win.sessionStorage.clear();
    });
    cy.clearCookies();
    cy.loginApi();
    cy.visit('');
  });

  beforeEach(() => {
    cy.visit('');
  });

  describe('/settings', () => {
    it('Language should be English', () => {
      cy.clickOnSettingsItem();
      cy.get('[data-cy=langKey]').should('contain', 'English');
    });

    it('Can change name and email', () => {
      cy.clickOnSettingsItem();
      cy.get('[data-cy=firstname]').clear().type('AdminLucas');
      cy.get('[data-cy=email]').clear().type('admin@localhost.com');
      cy.get('[data-cy=submit]').click();
      cy.visit('/');
      cy.clickOnSettingsItem();
      cy.get('[data-cy=firstname]').should('have.value', 'AdminLucas');
      cy.get('[data-cy=email]').should('have.value', 'admin@localhost.com');
    });
  });

  describe('/artist', () => {
    it('Can create a new artist', () => {
      cy.visit('/artist');
      cy.get('[data-cy=entityCreateButton]').click();
      cy.get('[data-cy=nick]').type('Firebolt');
      cy.get('[data-cy=firstName]').type('Lucas');
      cy.get('[data-cy=lastName]').type('Soria');
      cy.get('[data-cy=entityCreateSaveButton]').click();
      cy.get('[data-cy="entityTable"]').should('be.visible');
    });
  });
});
