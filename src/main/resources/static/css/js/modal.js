document.addEventListener('DOMContentLoaded', () => {
    const modal = document.getElementById('confirmationModal');
    const closeModal = document.getElementById('closeModal');

    // ��������� ��������� ����
    modal.style.display = 'block';

    // ��������� ��������� ����
    closeModal.addEventListener('click', () => {
        modal.style.display = 'none';
    });

    // �������� ��� ����� ��� ����
    window.addEventListener('click', (event) => {
        if (event.target === modal) {
            modal.style.display = 'none';
        }
    });
});
