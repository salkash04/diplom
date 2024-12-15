document.addEventListener('DOMContentLoaded', () => {
    const modal = document.getElementById('confirmationModal');
    const closeModal = document.getElementById('closeModal');

    // Открываем модальное окно
    modal.style.display = 'block';

    // Закрываем модальное окно
    closeModal.addEventListener('click', () => {
        modal.style.display = 'none';
    });

    // Закрытие при клике вне окна
    window.addEventListener('click', (event) => {
        if (event.target === modal) {
            modal.style.display = 'none';
        }
    });
});
