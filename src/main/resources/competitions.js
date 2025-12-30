/**
 * Script pentru gestionarea ștergerii multiple a competițiilor
 * Utilizat de reprezentanți pentru a șterge competiții în masă
 */
document.addEventListener('DOMContentLoaded', function() {
    const btnDelete = document.getElementById('btnDelete');

    if (btnDelete) {
        btnDelete.addEventListener('click', function() {
            const selectedCheckboxes = document.querySelectorAll('.delete-checkbox:checked');

            // Verifică dacă există cel puțin o competiție selectată
            if (selectedCheckboxes.length === 0) {
                alert('Vă rugăm să selectați cel puțin o competiție pentru a fi ștearsă.');
                return;
            }

            // Confirmă ștergerea
            const confirmMessage = `Atenție! Veți șterge ${selectedCheckboxes.length} competiții definitiv. Continuați?`;
            if (confirm(confirmMessage)) {
                // Creează formular dinamic pentru a trimite IDs-urile
                const form = document.createElement('form');
                form.method = 'POST';
                form.action = getContextPath() + '/DeleteCompetitions';

                // Adaugă fiecare ID selectat ca input hidden
                selectedCheckboxes.forEach(checkbox => {
                    const input = document.createElement('input');
                    input.type = 'hidden';
                    input.name = 'competition_ids';
                    input.value = checkbox.getAttribute('data-competition-id');
                    form.appendChild(input);
                });

                // Trimite formularul
                document.body.appendChild(form);
                form.submit();
            }
        });
    }
});

/**
 * Funcție helper pentru a obține contextPath-ul aplicației
 * @returns {string} Context path-ul aplicației
 */
function getContextPath() {
    return window.location.pathname.substring(0, window.location.pathname.indexOf("/", 2));
}