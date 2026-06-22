using Microsoft.Extensions.DependencyInjection;

namespace GigaChadSys.Servicios;

/// <summary>
/// Extensiones de IServiceCollection para registrar todos los servicios REST
/// que consumen el backend Java (GigaChadSys-REST) en el contenedor de DI.
///
/// Uso en Program.cs:
///   builder.Services.AddGigaChadSysServicios();
/// </summary>
public static class ServiciosExtensions
{
    public static IServiceCollection AddGigaChadSysServicios(this IServiceCollection services)
    {
        // Autenticación
        services.AddScoped<AuthServicio>();

        // Usuarios
        services.AddScoped<SocioServicio>();
        services.AddScoped<EntrenadorServicio>();
        services.AddScoped<AdministradorServicio>();

        // Clases y sesiones
        services.AddScoped<ClaseGrupalServicio>();
        services.AddScoped<SesionClaseServicio>();
        services.AddScoped<SalonServicio>();

        // Reservas
        services.AddScoped<ReservaServicio>();

        // Membresías y suscripciones
        services.AddScoped<MembresiaBasicServicio>();
        services.AddScoped<MembresiaBlackServicio>();
        services.AddScoped<SuscripcionServicio>();

        // Pagos
        services.AddScoped<PagoServicio>();
        services.AddScoped<MetodoPagoServicio>();

        return services;
    }
}
