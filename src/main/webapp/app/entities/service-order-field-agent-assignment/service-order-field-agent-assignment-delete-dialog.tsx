import React, { useEffect, useState } from 'react';
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import { Button, Modal, ModalBody, ModalFooter, ModalHeader } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { deleteEntity, getEntity } from './service-order-field-agent-assignment.reducer';

export const ServiceOrderFieldAgentAssignmentDeleteDialog = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();
  const { id } = useParams<'id'>();

  const [loadModal, setLoadModal] = useState(false);

  useEffect(() => {
    dispatch(getEntity(id));
    setLoadModal(true);
  }, []);

  const serviceOrderFieldAgentAssignmentEntity = useAppSelector(state => state.serviceOrderFieldAgentAssignment.entity);
  const updateSuccess = useAppSelector(state => state.serviceOrderFieldAgentAssignment.updateSuccess);

  const handleClose = () => {
    navigate('/service-order-field-agent-assignment');
  };

  useEffect(() => {
    if (updateSuccess && loadModal) {
      handleClose();
      setLoadModal(false);
    }
  }, [updateSuccess]);

  const confirmDelete = () => {
    dispatch(deleteEntity(serviceOrderFieldAgentAssignmentEntity.id));
  };

  return (
    <Modal isOpen toggle={handleClose}>
      <ModalHeader toggle={handleClose} data-cy="serviceOrderFieldAgentAssignmentDeleteDialogHeading">
        <Translate contentKey="entity.delete.title">Confirm delete operation</Translate>
      </ModalHeader>
      <ModalBody id="framasaasApp.serviceOrderFieldAgentAssignment.delete.question">
        <Translate
          contentKey="framasaasApp.serviceOrderFieldAgentAssignment.delete.question"
          interpolate={{ id: serviceOrderFieldAgentAssignmentEntity.id }}
        >
          Are you sure you want to delete this ServiceOrderFieldAgentAssignment?
        </Translate>
      </ModalBody>
      <ModalFooter>
        <Button color="secondary" onClick={handleClose}>
          <FontAwesomeIcon icon="ban" />
          &nbsp;
          <Translate contentKey="entity.action.cancel">Cancel</Translate>
        </Button>
        <Button
          id="jhi-confirm-delete-serviceOrderFieldAgentAssignment"
          data-cy="entityConfirmDeleteButton"
          color="danger"
          onClick={confirmDelete}
        >
          <FontAwesomeIcon icon="trash" />
          &nbsp;
          <Translate contentKey="entity.action.delete">Delete</Translate>
        </Button>
      </ModalFooter>
    </Modal>
  );
};

export default ServiceOrderFieldAgentAssignmentDeleteDialog;
