using System;

namespace GigaChadSys.Servicios
{
    public class ToastService
    {
        public event Action<string, string>? OnShow;

        public void ShowSuccess(string message)
        {
            OnShow?.Invoke("✅", message);
        }

        public void ShowError(string message)
        {
            OnShow?.Invoke("❌", message);
        }
        
        public void ShowInfo(string message)
        {
            OnShow?.Invoke("ℹ️", message);
        }
        
        public void ShowCustom(string icon, string message)
        {
            OnShow?.Invoke(icon, message);
        }
    }
}
