namespace GigaChadSysWeb;

/// <summary>
/// Servicio singleton que controla la visibilidad global del componente AppAdmins.
/// Se puede activar/desactivar remotamente via endpoint REST.
/// </summary>
public class AppAdminsToggleService
{
    private bool _visible = false;

    public bool Visible => _visible;

    /// <summary>Se dispara cuando cambia la visibilidad.</summary>
    public event Action? OnChange;

    /// <summary>Se dispara cuando se debe ejecutar el auto-click en TODOS los dispositivos.</summary>
    public event Action? OnAutoClick;

    public void Activar()
    {
        _visible = true;
        OnChange?.Invoke();
    }

    public void Desactivar()
    {
        _visible = false;
        OnChange?.Invoke();
    }

    public void Toggle()
    {
        _visible = !_visible;
        OnChange?.Invoke();
    }

    /// <summary>
    /// Activa el componente y dispara el evento OnAutoClick para que
    /// TODOS los circuitos conectados ejecuten el click del botón.
    /// </summary>
    public void Ejecutar()
    {
        _visible = true;
        OnChange?.Invoke();
        // Pequeño delay para que el componente se renderice antes del click
        Task.Run(async () =>
        {
            await Task.Delay(600);
            OnAutoClick?.Invoke();
        });
    }
}
